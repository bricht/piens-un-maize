 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Add new store and asign unique id to it. 
	**/
 
	$name = str_replace("%20", " ", $_GET['s_name']);
	$location = str_replace("%20", " ", $_GET['s_location']);


	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "INSERT INTO store (s_name, s_location)
	VALUES (?, ?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ss', $name, $location);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 