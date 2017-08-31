 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Delete barcode 
	**/
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "delete from barcode where b_barcode = ?";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('s', $barcode);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
?> 