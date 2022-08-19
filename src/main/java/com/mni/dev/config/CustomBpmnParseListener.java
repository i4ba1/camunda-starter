package com.mni.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomBpmnParseListener extends AbstractBpmnParseListener {
    private boolean isActivityOfTypeUserTask (ActivityImpl activity){
        return activity != null && activity.getProperty("type") != null &&
                activity.getProperty("type").toString().equals("userTask");
    }

    @Override
    public void parseSequenceFlow(Element sequenceFlowElement, ScopeImpl scopeElement, TransitionImpl transition) {
        try {
            // check if sequence source is usertask
            ActivityImpl sourceActivity = transition.getSource();
            if (isActivityOfTypeUserTask(sourceActivity)) {
                String sequenceName = sequenceFlowElement.attribute("name");
                if (sequenceName != null && sequenceName.startsWith("UserChoice")) {
                    // do nothing
                    log.info("Validation success for sequence [{}], name start with UserChoice", sequenceName);
                } else{
                    log.error("Validation fails for sequence [{}], name does not start with UserChoice", sequenceName);
                }
            }
        } catch (Exception e) {
            log.error("Failed parseSequenceFlow.", e);
        }
    }
}
