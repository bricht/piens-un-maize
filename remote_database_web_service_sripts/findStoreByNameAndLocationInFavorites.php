 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return stores in user favorites where store name and location 
	/* is like 'name_key' and 'location_key'. 
	**/
 
	$s_name = '%' . str_replace("%20", " ", $_GET['s_name']) . '%';
	$s_location = '%' . str_replace("%20", " ", $_GET['s_location']) . '%';
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select distinct store.*
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id 
            JOIN favoritestore on favoritestore.fs_storeID = store.s_id
			JOIN product on storeproductprice.spp_productID = product.p_id
			where 
			store.s_name like ? 
			and 
			store.s_location like ? 
			and 
			favoritestore.fs_userID = ?
			limit 200";
			
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('is', $u_id, $p_name);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed! " . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
?> 