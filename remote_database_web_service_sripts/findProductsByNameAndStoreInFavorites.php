 <?php
 
  	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return products in Store and Favorites where product name is like 'key'.
	**/
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_name = '%' . str_replace("%20", " ", $_GET['p_name']) . '%';
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select product.*, 
			store.*, 
			storeproductprice.spp_price, storeproductprice.spp_last_update
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id 
			JOIN product on storeproductprice.spp_productID = product.p_id
            JOIN favoriteproduct on favoriteproduct.fp_productID = product.p_id
			where 
			spp_storeID = ? 
			and 
			favoriteproduct.fp_userID = ? 
			and 
			product.p_name like ?
			limit 200";
			
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('iis', $s_id, $u_id, $p_name);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	
	$stmt->close();
	$conn->close();
?> 