 <?php
 
	$name = str_replace("%20", " ", $_GET['name']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	
	$sql = "select * from store where s_name like '%$name%'";
	if($result) {
		$jsonData = array();
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			$jsonData[] = $row;
		}
		echo json_encode($jsonData);
		} else {
			echo "0 results";
		}
	} else {
		echo "-Error: sql query failed!";
	}

	$conn->close();
	
?> 