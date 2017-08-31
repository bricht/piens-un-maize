 <?php
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$price = str_replace("%20", " ", $_GET['spp_price']);
	$last_update = str_replace("%20", " ", $_GET['spp_last_update']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "update storeproductprice set  
	spp_price = ? , 
	spp_last_update = ?
	where spp_storeID = ? and 
	spp_productID = ?
	";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('dsii', $price, $last_update, $s_id, $p_id);
	if($stmt->execute()) {
		echo "777 StoreProductPrice updated";
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 