package org.codehaus.mojo.jdepend;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Run JDepend and generate a site report. Goal which generate the JDepend metrics.
 * 
 * @author Karl-Heinz Marbaise
 */
@Mojo( name = "generate-no-fork", requiresProject = true, defaultPhase = LifecyclePhase.SITE )
public class JDependNoForkMojo
    extends AbstractJDependMojo
{

}
