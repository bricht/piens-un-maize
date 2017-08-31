 <?php
	
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Create new user with assigend user data parameter.
	/* Return created unique user id. 
	**/
	
	$u_data = str_replace("%20", " ", $_GET['u_data']);
	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();
	
	$sql = "INSERT INTO user (u_data)
	VALUES (?)";
	
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('s', $u_data);
	if($stmt->execute()) {
		echo $conn->insert_id;
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 