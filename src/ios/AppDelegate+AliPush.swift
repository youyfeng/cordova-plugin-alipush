import CloudPushSDK
import UserNotifications

extension AppDelegate {
    
    override open func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey : Any]? = nil) -> Bool {
        self.viewController = MainViewController()
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
        CloudPushSDK.sendNotificationAck(userInfo)
        print("Notification, alert: \(alert), badge: \(badge), sound: \(sound), extras: \(String(describing: extras)).");
        AliPushPlugin.fireNotificationEvent(object: ["content":alert, "badge":badge, "eventType":"receiveNotification", "extras":userInfo]);
    }
}
