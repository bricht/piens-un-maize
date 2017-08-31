 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return Product price in store + poruct and store objects.
	**/
 
	$p_id = str_replace("%20", " ", $_GET['p_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select product.*, 
			store.*, 
			storeproductprice.spp_price, storeproductprice.spp_last_update
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id
			JOIN product on storeproductprice.spp_productID = product.p_id
			where spp_productID = ?
			limit 200";			
			
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('i', $p_id);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	$stmt->close();
	$conn->close();
?> 