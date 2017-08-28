 <?php
 
	$u_data = str_replace("%20", " ", $_GET['u_data']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "INSERT INTO user (u_data)
	VALUES ('$u_data')";

	if ($conn->query($sql) === TRUE) {
		echo $conn->insert_id;
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 