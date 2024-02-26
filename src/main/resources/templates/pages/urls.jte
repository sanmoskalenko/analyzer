@import analyzer.code.dto.UrlsPage
@import analyzer.code.utils.NamedRoutes
@param UrlsPage page


@template.layout.page(
page = page,
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Websites</h1>

            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Name</th>
                    <th class="col-2">Last check</th>
                    <th class="col-1">HTTP Code</th>
                </tr>
                </thead>
                <tbody>
                @if(page != null && page.getUrls() != null)
                    @for(var url : page.getUrls())
                        <tr>
                            <td>${url.getId().toString()}</td>
                            <td>
                                <a href="${NamedRoutes.urlPath(url.getId().toString())}">${url.getName()}</a>
                            </td>
                            @if(url.getLastCheck() != null)
                                <td>${url.getLastCheck().getCreatedAt()}</td>
                                <td>${url.getLastCheck().getStatusCode()}</td>
                            @else
                                <td></td>
                                <td></td>
                            @endif
                        </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>
    </section>
`
)