@import analyzer.code.dto.UrlPage
@param UrlPage page


@template.layout.page(
page = page,
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Website: ${page.getName()}</h1>

            <table class="table table-bordered table-hover mt-3">
                <tbody>
                <tr>
                    <td>ID</td>
                    <td>${page.getId().toString()}</td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td>${page.getName()}</td>
                </tr>
                <tr>
                    <td>Creation date</td>
                    <td>${page.getCreatedAt()}</td>
                </tr>
                </tbody>
            </table>

            <h2 class="mt-5">Checks</h2>
            <form method="post" action="/urls/${page.getId()}/checks">
                <button type="submit" class="btn btn-primary">Run check</button>
            </form>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>HTTP Code</th>
                    <th>Title</th>
                    <th>H1</th>
                    <th>Description</th>
                    <th>Check date</th>
                </tr>
                </thead>
                <tbody>
                @if(page.getChecks() != null)
                    @for(var url : page.getChecks())
                        <tr>
                            <td>${url.getId().toString()}</td>
                            <td>${url.getStatusCode()}</td>
                            <td>${url.getTitle()}</td>
                            <td>${url.getH1()}</td>
                            <td>${url.getDescription()}</td>
                            <td>${url.getCreatedAt()}</td>
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>
    </section>
`
)