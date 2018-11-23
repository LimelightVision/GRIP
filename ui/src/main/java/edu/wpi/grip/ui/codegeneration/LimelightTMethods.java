package edu.wpi.grip.ui.codegeneration;


import com.google.common.base.CaseFormat;
import edu.wpi.grip.ui.codegeneration.data.TInput;
import edu.wpi.grip.ui.codegeneration.data.TStep;

import org.apache.commons.lang3.text.WordUtils;

public class LimelightTMethods extends TemplateMethods {
    public LimelightTMethods() {
        super();
    }

    @Override
    public String name(String name) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name.replaceAll("\\s", ""));
    }

    @Override
    public String getterName(String name) {
        return "Get" + WordUtils.capitalize(name(name));
    }

    @Override
    public String setterName(String name) {
        return name(name);
    }

    @Override
    public String callOp(TStep step) {
        StringBuilder out = new StringBuilder();
        if (step.name().equals("Switch") || step.name().equals("Valve")) {
            out.append("pipeline");
        }
        out.append(name(step.name())).append('(');
        for (TInput input : step.getInputs()) {
            out.append(name(input.name()));
            out.append(", ");
        }
        if (step.name().equals("Threshold_Moving")) {
            out.append("lastImage");
            out.append(step.num());
            out.append(", ");
        }
        if (!step.getOutputs().isEmpty()) {
            for (int i = 0; i < step.getOutputs().size() - 1; i++) {
                out.append(name(step.getOutputs().get(i).name()));
                out.append(", ");
            }
            out.append(name(step.getOutputs().get(step.getOutputs().size() - 1).name()));
        }
        out.append(')');
        return out.toString();
    }
}
