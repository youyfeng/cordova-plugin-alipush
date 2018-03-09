import CloudPushSDK
import UserNotifications

extension AppDelegate {
    
    override open func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey : Any]? = nil) -> Bool {
        self.viewController = MainViewController()
        UIApplication.shared.applicationIconBadgeNumber = 0;
        // 点击通知将App从关闭状态启动时，将通知打开回执上报
        //CloudPushSDK.handleLaunching(launchOptions)(Deprecated from v1.8.1)
        CloudPushSDK.sendNotificationAck(launchOptions);
        return super.application(application,didFinishLaunchingWithOptions:launchOptions);
    }
    
    open override func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        CloudPushSDK.registerDevice(deviceToken) {res in
            if (res!.success) {
                print("Upload deviceToken to Push Server, deviceToken: \(CloudPushSDK.getApnsDeviceToken()!), deviceId: \(CloudPushSDK.getDeviceId()!)")
            } else {
                print("Upload deviceToken to Push Server failed, error: \(String(describing: res?.error))")
            }
        }
    }
    
    override open func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("register for remote notifications error", error);
    }
    
    override open func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any]) {
        print("Receive one notification.")
        let aps = userInfo["aps"] as! [AnyHashable : Any]
        let alert = aps["alert"] ?? "none"
        let badge = aps["badge"] ?? 0
        let sound = aps["sound"] ?? "none"
        let extras = userInfo["Extras"]
        // 设置角标数为0
        application.applicationIconBadgeNumber = 0;
        // 同步角标数到服务端
        // self.syncBadgeNum(0)
        CloudPushSDK.sendNotificationAck(userInfo)
        print("Notification, alert: \(alert), badge: \(badge), sound: \(sound), extras: \(String(describing: extras)).");
        AliPushPlugin.fireNotificationEvent(object: ["content":alert, "badge":badge, "eventType":"receiveNotification", "extras":userInfo]);
    }
}
