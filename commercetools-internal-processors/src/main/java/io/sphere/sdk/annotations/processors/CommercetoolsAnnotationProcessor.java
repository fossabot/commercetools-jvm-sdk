package io.sphere.sdk.annotations.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class CommercetoolsAnnotationProcessor<A extends Annotation> extends AbstractProcessor {
    protected final Class<A> clazz;

    protected CommercetoolsAnnotationProcessor(final Class<A> clazz) {
        this.clazz = clazz;
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator var3 = roundEnv.getElementsAnnotatedWith(getAnnotationClass()).iterator();

        while(true) {
            Element element;
            do {
                if(!var3.hasNext()) {
                    return true;
                }

                element = (Element)var3.next();
            } while(!(element instanceof TypeElement));

            TypeElement typeElement = (TypeElement)element;
            generate(typeElement);
        }
    }

    protected final Class<? extends Annotation> getAnnotationClass() {
        return clazz;
    }

    protected abstract void generate(TypeElement typeElement);

    protected final void writeClass(final TypeElement typeElement, final String fullyQualifiedName, final String template, final Map<String, Object> values) {
        writeClass(typeElement, fullyQualifiedName, writer -> Templates.write(template, values, writer));
    }

    protected final void writeClass(final TypeElement typeElement, final ClassModel classModel) {
        writeClass(typeElement, classModel.getFullyQualifiedName(), writer -> Templates.writeClass(classModel, writer));
    }

    protected final void writeClass(final TypeElement typeElement, final String fullyQualifiedName, final CheckedConsumer<Writer> writerCheckedConsumer) {
        try {
            JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(fullyQualifiedName, new Element[]{typeElement});
            Writer writer = fileObject.openWriter();
            Throwable t = null;
            try {
                writerCheckedConsumer.accept(writer);
            } catch (Throwable throwable) {
                t = throwable;
                throw throwable;
            } finally {
                if(writer != null) {
                    if(t != null) {
                        try {
                            writer.close();
                        } catch (Throwable var24) {
                            t.addSuppressed(var24);
                        }
                    } else {
                        writer.close();
                    }
                }
            }
        } catch (Throwable e) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }

    public interface CheckedConsumer<T> {
        void accept(T t) throws Throwable;
    }

    protected final String getResourceName(final TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }
}
