// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AbsoluteDrive;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.commands.AbsoluteFieldDrive;
import frc.robot.commands.AbsoluteDriveAdv;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.SwerveSubsystem;
import java.io.File;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic
 * methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and
 * trigger mappings) should be declared here.
 */
public class RobotContainer {

    // The robot's subsystems and commands are defined here...
    private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
            "swerve"));
            
//     XboxController driverXbox = new XboxController(OperatorConstants.ControllerPort);
    //Joystick driverContraoller = new Joystick(0);
    //JoystickButton Y = new JoystickButton(driverCaontroller, 4);
    //JoystickButton X = new JoystickButton(drivearController, 3);
    //JoystickButton A = new JoystickButton(driveraController, 1);
    //JoystickButton B = new JoystickButton(driveraController, 2);

    PS4Controller ps4 = new PS4Controller(0);

    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();

        // AbsoluteDrive closedAbsoluteDrive = new AbsoluteDrive(drivebase,
        //         // Applies deadbands and inverts controls because joysticks
        //         // are back-right positive while robot
        //         // controls are front-left positive
        //         () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
        //                 OperatorConstants.LEFT_Y_DEADBAND),
        //         () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
        //                 OperatorConstants.LEFT_X_DEADBAND),
        //         () -> -driverXbox.getRightX(),
        //         () -> -driverXbox.getRightY());

        // AbsoluteFieldDrive closedFieldAbsoluteDrive = new AbsoluteFieldDrive(drivebase,
        //         () -> MathUtil.applyDeadband(driverXbox.getLeftY(),
        //                 OperatorConstants.LEFT_Y_DEADBAND),
        //         () -> MathUtil.applyDeadband(driverXbox.getLeftX(),
        //                 OperatorConstants.LEFT_X_DEADBAND),
        //         () -> driverXbox.getRawAxis(2));

         AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
                 () -> MathUtil.applyDeadband(ps4.getRawAxis(1),
                         OperatorConstants.LEFT_Y_DEADBAND),
                 () -> MathUtil.applyDeadband(ps4.getRawAxis(0),
                         OperatorConstants.LEFT_X_DEADBAND),
                 () -> MathUtil.applyDeadband(ps4.getRawAxis(2),
                         OperatorConstants.RIGHT_X_DEADBAND),
                 () -> ps4.getCircleButton(),
                 () -> ps4.getCrossButton(),
                 () -> ps4.getL1Button(),
                 () -> ps4.getL2Button());

        TeleopDrive closedFieldRel = new TeleopDrive(
                drivebase,
                () -> -MathUtil.applyDeadband(ps4.getRawAxis(2), OperatorConstants.LEFT_Y_DEADBAND),
                () -> -MathUtil.applyDeadband(ps4.getRawAxis(1), OperatorConstants.LEFT_X_DEADBAND),
                () -> -MathUtil.applyDeadband(ps4.getRawAxis(2), OperatorConstants.LEFT_X_DEADBAND), () -> true);

        // m_chooser.addOption("Closed Absolute Drive", closedAbsoluteDrive);
        // m_chooser.addOption("Closed Field Absolute Drive", closedFieldAbsoluteDrive);
        m_chooser.addOption("Closed Absolute Drive Adv", closedAbsoluteDriveAdv);
        m_chooser.setDefaultOption("TeleOp", closedFieldRel);

        SmartDashboard.putData(m_chooser);

        // drivebase.setDefaultCommand(!RobotBase.isSimulation() ? closedAbsoluteDrive :
        // closedFieldAbsoluteDrive);
        drivebase.setDefaultCommand(m_chooser.getSelected());
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary predicate, or via the
     * named factories in
     * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
     * for
     * {@link CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
     * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick
     * Flight joysticks}.
     */
    private void configureBindings() {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        // new JoystickButton(dri verXbox, 1).onTrue((new InstantCommand(drivebase::zeroGyro)));
        // new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
        // new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        // return drivebase.getAutonomousCommand("New Path", true);
        return m_chooser.getSelected();
    }

    public void setDriveMode() {
        // drivebase.setDefaultCommand();
    }

    public void setMotorBrake(boolean brake) {
        drivebase.setMotorBrake(brake);
    }
}