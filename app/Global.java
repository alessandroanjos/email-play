import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.SimpleResult;
import play.mvc.Http.RequestHeader;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import views.html.index;
import views.html.defaultpages.notFound;
import static play.mvc.Results.*;

public class Global extends GlobalSettings {

	@Override
	public void beforeStart(Application application) {
		Logger.info("Iniciando...");
		super.beforeStart(application);
	}

	@Override
	public void onStart(Application application) {
		Logger.info("Pronto.");
		super.onStart(application);
	}

	@Override
	public void onStop(Application application) {
		Logger.info("good bye cruel world");
		super.onStop(application);
	}

	/**
	 * If the framework doesn’t find an action method for a request,
	 *  the onHandlerNotFound operation will be called:
	 */
	@Override
	public Promise<SimpleResult> onHandlerNotFound(RequestHeader resquest) {
		return Promise.<SimpleResult> pure(notFound(
				views.html.notFound.render("Página não encontrada!")
			));
	}

	/**
	 * When an exception occurs in your application, the onError operation will be called. 
	 * The default is to use the internal framework error page. You can override this:
	 */
	@Override
	public Promise<SimpleResult> onError(RequestHeader request, Throwable throwable) {
		return Promise.<SimpleResult>pure(internalServerError(
	            views.html.error.render(throwable)
	        ));
	}

	/**
	 * The onBadRequest operation will be called if a route was found, 
	 * but it was not possible to bind the request parameters:
	 */
	@Override
	public Promise<SimpleResult> onBadRequest(RequestHeader arg0, String arg1) {
		return Promise.<SimpleResult>pure(badRequest("Don't try to hack the URI!"));
	}
}
