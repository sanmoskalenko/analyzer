@import analyzer.code.utils.NamedRoutes
@import gg.jte.Content
@import analyzer.code.dto.Alert
@import analyzer.code.dto.BasePage
@import java.util.stream.Collectors
@import io.javalin.validation.ValidationError
@param BasePage page
@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Page Analyzer</title>
    <meta name="description" content="Page Analyzer is a site that analyzes specified pages for SEO suitability">
    <link rel="icon" sizes="any" type="image/svg+xml"
          href="https://raw.githubusercontent.com/googlefonts/noto-emoji/f2a4f72bffe0212c72949a22698be235269bfab5/svg/emoji_u1f916.svg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>
</head>
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Page Analyzer</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="navbar-nav">
                <a class="nav-link" href="/">Main</a>
                <a class="nav-link" href="${NamedRoutes.urlsPath()}">Websites</a>
            </div>
        </div>
    </div>
</nav>
<main class="flex-grow-1">
    @if (page != null && page.getAlert() != null)
        @if(page.getAlert().getType() == Alert.TYPE.INFO)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-info" role="alert">
                <p class="m-0">
                    ${page.getAlert().getMessage()}
                </p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @elseif(page.getAlert().getType() == Alert.TYPE.SUCCESS)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
                <p class="m-0">${page.getAlert().getMessage()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @elseif(page.getAlert().getType() == Alert.TYPE.ERROR)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-danger" role="alert">
                <p class="m-0">${page.getAlert().getMessage()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @elseif (page.getValidationErrors() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-danger" role="alert">
                <p class="m-0">
                    ${page.getValidationErrors().values().stream()
                                .map(error -> error.stream()
                                        .map(ValidationError::getMessage)
                                        .collect(Collectors.joining("; ")))
                                .collect(Collectors.joining())}
                </p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
    @endif
    @if (page != null && page.getValidationErrors() != null)
    <div class="rounded-0 m-0 alert alert-dismissible fade show alert-danger" role="alert">
        <p class="m-0">
            ${page.getValidationErrors().values().stream()
                                .map(error -> error.stream()
                                        .map(ValidationError::getMessage)
                                        .collect(Collectors.joining("; ")))
                                .collect(Collectors.joining())}
        </p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    @endif
    ${content}
</main>

<footer class="footer border-top py-3 mt-5 bg-light">
    <div class="container-xl">
        <div class="text-center ">
            created by
            <p>
                <a href="https://github.com/sanmoskalenko" class="text-decoration-none">
                    sanmoskalenko
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-github" viewBox="0 0 16 16">
                        <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.012 8.012 0 0 0 16 8c0-4.42-3.58-8-8-8"></path>
                    </svg>
                </a>
            </p>
        </div>
    </div>
</footer>
</body>
</html>

