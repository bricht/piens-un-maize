 <?php
 
	$u_id = str_replace("%20", " ", $_GET['u_id']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "select  
			product.* 
			from favoriteproduct
			JOIN product on favoriteproduct.fp_productID = product.p_id
			JOIN user on favoriteproduct.fp_userID = user.u_id
			where fp_userID = $u_id";
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
?> 