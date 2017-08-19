 <?php
 
	$name = str_replace("%20", " ", $_GET['name']);
	$location = str_replace("%20", " ", $_GET['location']);


	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "INSERT INTO store (s_name, s_location)
	VALUES ('$name', '$location')";

	if ($conn->query($sql) === TRUE) {
		echo "+New record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 