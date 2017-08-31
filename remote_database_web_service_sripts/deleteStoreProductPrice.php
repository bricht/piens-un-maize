 <?php
 
   	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Remove product and its price from store.
	**/
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_id = str_replace("%20", " ", $_GET['p_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "delete from storeproductprice  
			where 
			spp_storeID = ?
			and 
			spp_productID = ?
	";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ii', $s_id, $p_id);
	if($stmt->execute()) {
		echo "affected rows: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 