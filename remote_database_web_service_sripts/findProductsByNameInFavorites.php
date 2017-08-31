 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return products in user favorites where product name is like 'key'. 
	**/
 
	$p_name = '%' . str_replace("%20", " ", $_GET['p_name']) . '%';
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select distinct product.*
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id 
			JOIN product on storeproductprice.spp_productID = product.p_id
            JOIN favoriteproduct on favoriteproduct.fp_productID = product.p_id
			where 
			favoriteproduct.fp_userID = ? 
			and 
			product.p_name like ?
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