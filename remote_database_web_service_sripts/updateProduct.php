 <?php
	
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$name = str_replace("%20", " ", $_GET['p_name']);
	$category = str_replace("%20", " ", $_GET['p_category']);
	$description = str_replace("%20", " ", $_GET['p_descript']);
	$price = str_replace("%20", " ", $_GET['p_price']);


	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "update product set 
	p_name = '$name', 
	p_category = '$category', 
	p_descript = '$description' , 
	p_price = $price
	where p_id = $p_id";

	if ($conn->query($sql) === TRUE) {
		echo "+New record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 