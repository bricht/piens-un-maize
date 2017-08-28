 <?php
	
	$s_id = str_replace("%20", " ", $_GET['s_id']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "delete from storeproductprice where spp_storeID = $s_id;
			delete from store where s_id = $s_id";

	if ($conn->query($sql) === TRUE) {
		echo "Record deleted.";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 