 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return store with product where store name and location 
	/* is like 'name key' and 'location key'.
	**/
 
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$s_name = '%' . str_replace("%20", " ", $_GET['s_name']) . '%';
	$s_location = '%' . str_replace("%20", " ", $_GET['s_location']) . '%';
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select product.*, 
			store.*, 
			storeproductprice.spp_price, storeproductprice.spp_last_update
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id
			JOIN product on storeproductprice.spp_productID = product.p_id
			where 
			spp_productID = ? 
			and 
			store.s_name like ?
			and 
			store.s_location like ?
			limit 200";
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('iss', $p_id, $s_name, $s_location);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed! " . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
?> 