/**
 * 
 */
package std;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.dominik.coda.CodaRuntime;
import at.dominik.coda.exceptions.CodaExceptionTransporter;
import at.dominik.coda.scope.block.CodaObject;
import at.dominik.coda.scope.block.Function;
import at.dominik.coda.scope.block.headers.CodaClass;
import at.dominik.coda.values.Value;
import coda.lang.CodaByte;

/**
 * @author Dominik Fluch
 *
 * Created on 08.02.2020
 *
 */
public class IOUtil {

	/**
	 * @param runtime
	 * @param javaOutputStream
	 * @return the outputStream converted into a coda one.
	 */
	public static CodaObject toCodaOutputStream(CodaRuntime runtime, OutputStream javaOutputStream) {
		final CodaClass outputStreamClass = (CodaClass)runtime.get(runtime, "coda").get(runtime, "io").get(runtime, "OutputStream");
		final CodaObject outputStream = outputStreamClass.createInstance(runtime.getGlobalScope()).getContent();
		
		final Function initialWrite = (Function) outputStream.getElement(runtime.getGlobalScope(), "write", Function.class, runtime.getType(runtime.getGlobalScope(), "coda.lang.byte"));
		final Function initialFlush = (Function) outputStream.getElement(runtime.getGlobalScope(), "flush", Function.class);
		final Function initialClose = (Function) outputStream.getElement(runtime.getGlobalScope(), "close", Function.class);
		
		outputStream.getElements().remove(initialWrite);
		outputStream.getElements().remove(initialFlush);
		outputStream.getElements().remove(initialClose);
		
		outputStream.getElements().add(new Function(outputStream, initialWrite.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					javaOutputStream.write(((CodaByte)arguments[0].getContent()).getValue().intValue());
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
				
				return Value.NULL;
			}
			
		}.setImports(outputStreamClass.getImports()));
		outputStream.getElements().add(new Function(outputStream, initialFlush.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					javaOutputStream.flush();
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
				
				return Value.NULL;
			}
			
		}.setImports(outputStreamClass.getImports()));
		outputStream.getElements().add(new Function(outputStream, initialClose.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					javaOutputStream.close();
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
				
				return Value.NULL;
			}
			
		}.setImports(outputStreamClass.getImports()));
		
		return outputStream;
	}
	
	/**
	 * @param runtime
	 * @param inputStream
	 * @return the inputStream converted into a coda one.
	 */
	public static CodaObject toCodaInputStream(CodaRuntime runtime, InputStream javaInputStream) {
		final CodaClass inputStreamClass = (CodaClass)runtime.get(runtime, "coda").get(runtime, "io").get(runtime, "InputStream");
		final CodaObject inputStream = inputStreamClass.createInstance(runtime.getGlobalScope()).getContent();
		
		final Function initialRead = (Function) inputStream.getElement(runtime.getGlobalScope(), "read", Function.class);
		final Function initialAvailable = (Function) inputStream.getElement(runtime.getGlobalScope(), "available", Function.class);
		final Function initialClose = (Function) inputStream.getElement(runtime.getGlobalScope(), "close", Function.class);
		
		inputStream.getElements().remove(initialRead);
		inputStream.getElements().remove(initialAvailable);
		inputStream.getElements().remove(initialClose);
		
		inputStream.getElements().add(new Function(inputStream, initialRead.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					return new Value(runtime.getEvaluator().createByte(this, (byte)javaInputStream.read()));
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
			}
			
		}.setImports(inputStream.getImports()));
		inputStream.getElements().add(new Function(inputStream, initialAvailable.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					return new Value(runtime.getEvaluator().createInteger(this, javaInputStream.available()));
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
			}
			
		}.setImports(inputStream.getImports()));
		inputStream.getElements().add(new Function(inputStream, initialClose.getImage().setAbstract(false)) {
			
			@Override
			public Value get(Value... arguments) {
				super.get(arguments);
				
				try {
					javaInputStream.close();
				} catch (IOException exception) {
					throw new CodaExceptionTransporter(this.getCodeLine(), ((CodaClass)this.getRuntime().get(this, "coda").get(this, "io").get(this, "IOException")).createInstance(this, new Value(this.getRuntime().getEvaluator().createString(this, exception.getMessage()))).getContent());
				}
				
				return Value.NULL;
			}
			
		}.setImports(inputStream.getImports()));
		
		return inputStream;
	}
	
}
