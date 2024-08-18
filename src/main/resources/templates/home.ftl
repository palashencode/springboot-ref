<html>
<head>
    <meta http-equiv="Content-Type"
          content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet"  href="css/bootstrap.css">
    <title>Home Page</title>
    <style>
        .center {
            margin: auto;
            width: 60%;
            border: 5px solid #ABABAB;
            padding: 10px;
            margin-top: 100px;
        }
    </style>
</head>
<body>
<div class="center">
    <h3>${msg}</h3>
    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th>Feature Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <#list features as f>
            <tr>
                <td>${f.name}</td>
                <td>${f.description}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
</body>
</html>