 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Add product and its coresponding barcode
	**/
 
	$name = str_replace("%20", " ", $_GET['p_name']);
	$category = str_replace("%20", " ", $_GET['p_category']);
	$description = str_replace("%20", " ", $_GET['p_descript']);
	$price = str_replace("%20", " ", $_GET['p_price']);
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);


	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "	INSERT INTO product (p_name, p_category, p_descript , p_price)
				VALUES (?, ?, ?, ?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('sssd', $name, $category, $description, $price);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$sql = "	INSERT INTO barcode (b_barcode, b_productID)
				VALUES(?, LAST_INSERT_ID());";
				
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