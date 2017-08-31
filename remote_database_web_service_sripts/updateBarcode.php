 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Update product identity aissigned to particualr barcode
	**/
	
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);
	$productId = str_replace("%20", " ", $_GET['b_productID']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "update barcode set
	b_productID = ?
	where b_barcode = ?";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('is', $productId, $barcode);
	
	if($stmt->execute()) {
		echo "777 Barcode updated";
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 