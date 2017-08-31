 <?php
 
 	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return all Products form database.
	**/
 
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "select * from product limit 100000";
	$stmt = $conn->prepare($sql);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 