 <?php
 
	$name = str_replace("%20", " ", $_GET['p_name']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	
	
	
	$sql = "select * from product where p_name like '%$name%' order by p_price limit 200";
	$result = $conn->query($sql);
	$jsonData = array();
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$jsonData[] = $row;
		}
		echo json_encode($jsonData);
	} else {
		echo "[]";
	}

	$conn->close();
	
?> 