package com.mysema.query.collections;

import java.util.Collections;

import com.google.common.base.Function;
import com.mysema.codegen.Evaluator;
import com.mysema.query.EmptyMetadata;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathExtractor;
import com.mysema.query.types.Predicate;

/**
 * GuavaHelpers provides functionality to wrap Querydsl {@link Predicate} instances to Guava predicates
 * and Querydsl {@link Expression} instances to Guava functions 
 * 
 * @author tiwe
 *
 */
public final class GuavaHelpers {
    
    private static final DefaultEvaluatorFactory evaluatorFactory = 
            new DefaultEvaluatorFactory(ColQueryTemplates.DEFAULT);
    
    /**
     * Wrap a Querydsl predicate into a Guava predicate
     * 
     * @param predicate
     * @return
     */
    public static <T> com.google.common.base.Predicate<T> wrap(Predicate predicate) {        
        Path<?> path = (Path<?>)predicate.accept(PathExtractor.DEFAULT, null);
        if (path != null) {
            final Evaluator<Boolean> ev = createEvaluator(path.getRoot(), predicate);
            return new com.google.common.base.Predicate<T>() {
                @Override
                public boolean apply(T input) {
                    return ev.evaluate(input);
                }                
            };
        } else {
            throw new IllegalArgumentException("No path in " + predicate);
        }
    }
    
    /**
     * Wrap a Querydsl expression into a Guava function
     * 
     * @param projection
     * @return
     */
    public static <F,T> Function<F,T> wrap(Expression<T> projection) {        
        Path<?> path = (Path<?>)projection.accept(PathExtractor.DEFAULT, null);
        if (path != null) {
            final Evaluator<T> ev = createEvaluator(path.getRoot(), projection);
            return new Function<F,T>() {
                @Override
                public T apply(F input) {
                    return ev.evaluate(input);
                }                
            };
        } else {
            throw new IllegalArgumentException("No path in " + projection);
        }
    }
    
    private static <F,T> Evaluator<T> createEvaluator(Path<F> path, Expression<T> projection) {
        return evaluatorFactory.create(EmptyMetadata.DEFAULT, 
                Collections.singletonList(path), projection);
    }
    
    private GuavaHelpers() {  }

}
