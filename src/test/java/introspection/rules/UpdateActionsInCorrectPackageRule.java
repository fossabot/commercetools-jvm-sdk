package introspection.rules;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;

public class UpdateActionsInCorrectPackageRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return isSubTypeOf(clazz, UpdateAction.class) && !clazz.equals(UpdateAction.class) && !clazz.equals(UpdateActionImpl.class);
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".updateactions");
    }
}