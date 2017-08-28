 <?php
	
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$name = str_replace("%20", " ", $_GET['s_name']);
	$location = str_replace("%20", " ", $_GET['s_location']);


	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "update store set s_name = '$name', s_location = '$location' where s_id = $s_id";

	if ($conn->query($sql) === TRUE) {
		echo "+New record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 