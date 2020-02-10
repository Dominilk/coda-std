/**
 * 
 */
package std;

import at.dominik.coda.CodaRuntime;
import at.dominik.coda.scope.block.CodaObject;
import at.dominik.coda.scope.block.Function;
import at.dominik.coda.scope.block.headers.CodaClass;import at.dominik.coda.values.Value;

/**
 * @author Dominik Fluch
 *
 * Created on 08.02.2020
 *
 */
public class Init {

	/**
	 * Initializes the std library.
	 * @param runtime
	 */
	public static void init(CodaRuntime runtime) {
		final CodaClass system = (CodaClass) runtime.get(runtime, "coda").get(runtime, "lang").get(runtime, "System");
		final CodaObject stdIO = system.getStaticElement(runtime.getGlobalScope(), "getStandardIO", Function.class).get().getContent();
		stdIO.getElement(runtime.getGlobalScope(), "setOutputStream", Function.class, runtime.getType(runtime.getGlobalScope(), "coda.io.OutputStream")).get(new Value(IOUtil.toCodaOutputStream(runtime, System.out)));
	}
	
}
