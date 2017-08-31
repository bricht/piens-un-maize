 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Add product and its price to store
	**/
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$price = str_replace("%20", " ", $_GET['spp_price']);
	$last_update = str_replace("%20", " ", $_GET['spp_last_update']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "INSERT INTO storeproductprice (spp_storeID, spp_productID, spp_price , spp_last_update)
	VALUES (?, ?, ?, ?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('iids', $s_id, $p_id, $price, $last_update);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 