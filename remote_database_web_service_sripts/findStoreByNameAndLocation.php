 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Find Stores where store name contains 'name' and store location contains 'location'
	**/
 
	$name = '%' . str_replace("%20", " ", $_GET['s_name']) . '%';
	$location = '%' . str_replace("%20", " ", $_GET['s_location']) . '%';

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
			
	$sql = "select * from store 
	where s_name like ? 
	and 
	s_location like ? 
	limit 200";
	
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ss', $name, $location);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	
	$stmt->close();
	$conn->close();
	
?> 