 <?php
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);
	$productId = str_replace("%20", " ", $_GET['b_productID']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
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