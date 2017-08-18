 <?php
 
	$name = str_replace("%20", " ", $_GET['name']);
	$category = str_replace("%20", " ", $_GET['category']);
	$description = str_replace("%20", " ", $_GET['description']);
	$price = str_replace("%20", " ", $_GET['price']);


	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "INSERT INTO product (p_name, p_category, p_description , p_price)
	VALUES ('$name', '$category', '$description', '$price')";

	if ($conn->query($sql) === TRUE) {
		echo "+New record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 