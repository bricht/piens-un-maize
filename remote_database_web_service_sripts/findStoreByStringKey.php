 <?php
	
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return Stores where store name or location is like 'key'
	**/
	
	$str_key = '%' . str_replace("%20", " ", $_GET['str_key']) . '%';

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select * from store 
	where 
	s_name like ? 
	or 
	s_location like ? 
	limit 200";
	
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ss', $str_key, $str_key);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
?>