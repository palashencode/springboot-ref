<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Error Page</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/style.css">
    <script src="/js/bootstrap.js"></script>
  </head>
  <body>
    <main role="main" class="container">
      <div class="starter-template text-left" style="padding-top : 1px">
        <h1>Oops! Something went wrong !</h1>
        <p>Timestamp - ${.now?iso_utc_m}</p>
        <p class="alert alert-danger" role="alert" >${errmsg}</p>
        <a class="btn btn-primary" href="/">Go Home</a>
      </div>
    </main>
  </body>
</html>

