 <?php
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);
	$productId = str_replace("%20", " ", $_GET['b_productID']);

	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "delte from barcode where b_barcode = '$barcode'";

	if ($conn->query($sql) === TRUE) {
		echo "Sucsess.";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 