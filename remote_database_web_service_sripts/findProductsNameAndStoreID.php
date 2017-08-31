 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Find and return products in Store by name.
	**/
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_name = '%' . str_replace("%20", " ", $_GET['p_name']) . '%';
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select store.*, product.*, storeproductprice.spp_price, storeproductprice.spp_last_update 
			from
			store, product, storeproductprice
            where product.p_name like ? 
			and 
			store.s_id = ?
			limit 200";
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('si', $p_name, $s_id);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed! " . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 