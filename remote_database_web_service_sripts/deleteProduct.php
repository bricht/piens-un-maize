 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Delete Product instance and all product references in other tables.
	**/
	
	$p_id = str_replace("%20", " ", $_GET['p_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sqls =  array(
	
		"delete from storeproductprice WHERE spp_productID = ?",
		"delete from favoriteproduct where fp_productID = ?",
		"delete from product WHERE p_id = ?"
		
		);

	foreach($sqls as &$sql) {
		$stmt = $conn->prepare($sql);
		$stmt->bind_param('i', $p_id);
		if($stmt->execute()) {
			echo "Rows affected: " . $conn->affected_rows . "\n\r";
		} else {
			echo "Error: sql query failed!" . $conn->error;
		}
	}
	
	$stmt->close();
	$conn->close();
?> 