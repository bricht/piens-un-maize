 <?php
 
 	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return store where sotre name is like 'name key'
	**/
 
	$name = '%' . str_replace("%20", " ", $_GET['s_name']) . '%';

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "select * from store 
	where 
	s_name like ?' 
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