<nav class="navbar navbar-expand-lg navbar-light">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item"><a class="nav-link" href="<@spring.url '/account'/>"><strong>My Account</strong></a></li>
                        </@security.authorize>
                            <li class="nav-item"><a class="nav-link" href="<@spring.url '/events'/>"><strong>Events</strong></a></li>
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item"><a class="nav-link" href="#"><strong>Tickets</strong></a></li>
                        </@security.authorize>
                        <@security.authorize access="hasRole('ROLE_ADMIN')">
                            <li class="nav-item"><a class="nav-link" href="<@spring.url '/users'/>"><strong>Users</strong></a></li>
                        </@security.authorize>
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item"><a class="nav-link" href="<@spring.url '/info'/>"><strong>Info</strong></a></li>
                        </@security.authorize>

        </ul>
                    <@security.authorize access="isAnonymous()">
                    <form class="form-inline my-2 my-lg-0" role="form" action="<@spring.url '/events'/>" method="post">
                        <input type="text" class="form-control mr-sm-2" name='username' placeholder="Email" aria-label="Email">
                        <input type="password" class="form-control mr-sm-2" name='password' placeholder="Password" aria-label="Password">
                        <div>
                            <label for="remember-me">Remember me</label>
                            <input type="checkbox" name="remember-me" id="remember-me"/>
                        </div>
                        <button class="btn btn-outline-primary my-2 my-sm-0 button-marg type="submit">Sign in</button>
                        <a class="btn btn-outline-primary my-2 my-sm-0 button-marg" role="button" href="#">Register &raquo;</a>
                    </form>
                    </@security.authorize>

                    <@security.authorize access="isAuthenticated()">
                        <p class="user-style marg-left-20">Logged as: <strong><@security.authentication property="principal.username"></@security.authentication></strong></p>
                        <p class="user-style marg-left-20">Balance: <strong><@security.authentication property="principal.balance"></@security.authentication> UAH</strong></p>
                        <a href="<@spring.url '/logout'/>">
                            <p class="user-style marg-left-20"><strong>Logout</strong></p>
                        </a>
                    </@security.authorize>
    </div>
</nav>