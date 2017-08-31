 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Update product fields if product id is found
	**/
	
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$name = str_replace("%20", " ", $_GET['p_name']);
	$category = str_replace("%20", " ", $_GET['p_category']);
	$description = str_replace("%20", " ", $_GET['p_descript']);
	$price = str_replace("%20", " ", $_GET['p_price']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "update product set 
	p_name = ?, 
	p_category = ?, 
	p_descript = ?, 
	p_price = ?
	where p_id = ?";
	
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('sssdi', $name, $category, $description, $price, $p_id);
	if($stmt->execute()) {
		echo "777 Product updated";
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 