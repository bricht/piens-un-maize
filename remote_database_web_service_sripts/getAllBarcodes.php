 <?php
	
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return all barcodes from database.
	**/
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "select * from barcode limit 100000";
	$stmt = $conn->prepare($sql);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	
	$stmt->close();
	$conn->close();
	
?> 