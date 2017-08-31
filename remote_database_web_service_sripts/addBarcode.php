 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Add new barcode to existing product 
	**/
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);
	$productId = str_replace("%20", " ", $_GET['b_productID']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "INSERT INTO barcode (b_barcode, b_productID)
	VALUES (?,?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('si', $barcode, $productId);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 