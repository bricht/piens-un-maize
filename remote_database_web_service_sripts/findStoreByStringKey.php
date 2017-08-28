 <?php
 
	$str_key = str_replace("%20", " ", $_GET['str_key']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	
	$sql = "select * from store where s_name like '%$str_key%' or s_location like '%$str_key%' limit 200";
	$result = $conn->query($sql);
	if($result) {
		$jsonData = array();
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$jsonData[] = $row;
		}
		echo json_encode($jsonData);
		} else {
			echo "[]";
		}
	} else {
		echo "-Error: sql query failed!";
	}
	

	$conn->close();