 <?php
 
	$barcode = str_replace("%20", " ", $_GET['barcode']);
	$productId = str_replace("%20", " ", $_GET['p_id']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "INSERT INTO barcode (code, productID)
	VALUES ('$barcode', '$productId')";

	if ($conn->query($sql) === TRUE) {
		echo "+New record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}
	
	$file = 'log.txt';
	$current = file_get_contents($file);
	$current .= "File Trigered";
	file_put_contents($file, $current);

	$conn->close();
	
?> 