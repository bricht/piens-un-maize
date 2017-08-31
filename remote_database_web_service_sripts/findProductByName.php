 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return products where product name is like 'key'.
	**/
 
	$name = '%' . str_replace("%20", " ", $_GET['p_name']) . '%';

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "select * from product 
			where 
			p_name like ? 
			order 
			by p_price 
			limit 200";
	
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('s', $name);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed! " . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 