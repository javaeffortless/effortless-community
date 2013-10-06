package org.effortless.gen;

import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.ast.ClassNode;

import groovy.transform.CompilationUnitAware;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

public class CodeCustomizer extends CompilationCustomizer implements CompilationUnitAware {
    private final ASTTransformation transformation;

    protected CompilationUnit compilationUnit;

    /**
     * Creates an AST transformation customizer using the specified transformation.
     */
    public CodeCustomizer(final ASTTransformation transformation) {
        super(findPhase(transformation));
        this.transformation = transformation;
    }

    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    private static CompilePhase findPhase(ASTTransformation transformation) {
        if (transformation==null) throw new IllegalArgumentException("Provided transformation must not be null");
        final Class<?> clazz = transformation.getClass();
        final GroovyASTTransformation annotation = clazz.getAnnotation(GroovyASTTransformation.class);
        if (annotation==null) throw new IllegalArgumentException("Provided ast transformation is not annotated with "+GroovyASTTransformation.class.getName());

        return annotation.phase();
    }

    @Override
    public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        if (transformation instanceof CompilationUnitAware) {
            ((CompilationUnitAware)transformation).setCompilationUnit(this.compilationUnit);
        }
        // this is a global AST transformation
        this.transformation.visit(null, source);
    }
    
}
