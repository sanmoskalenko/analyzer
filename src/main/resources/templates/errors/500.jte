@template.layout.page(
page = null,
content = @`
    <section>
        <div class="container-fluid bg-dark p-5">
            <div class="row">
                <div class="col-md-10 col-lg-8 mx-auto text-white">
                    <div class="container-fluid bg-dark p-5">
                        <div class="row">
                            <h1 class="display-3 mb-0">Something went wrong :(</h1>
                            <div class="row mt-5">
                                <div class="col-auto">
                                    <a class="btn btn-lg btn-primary px-sm-5"
                                       href="${analyzer.code.utils.NamedRoutes.index()}">
                                        Try again
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
`
)