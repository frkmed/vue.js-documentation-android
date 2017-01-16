<?php

require_once __DIR__ . '/vendor/autoload.php';

class Handler
{
    /**
     * @var \Illuminate\Filesystem\Filesystem
     */
    protected $filesystem;

    /**
     * @var string
     */
    protected $directory = __DIR__ . '\\vuejs.org\\public';

    /**
     * @return void
     */
    public function __construct()
    {
        $this->filesystem = new \Illuminate\Filesystem\Filesystem();
    }

    /**
     * @return void
     */
    public function handle()
    {
        $this->git()->pkg()
            ->generate();

        $ruler = $this->ruler();

        /** @var SplFileInfo $fileInfo */
        foreach ($this->filesystem->allFiles($this->directory) as $fileInfo) {

            switch ($fileInfo->getExtension()) {
                case 'html':

                    $getContents = $this->filesystem->get($fileInfo->getRealPath());

                    if (preg_match_all('/href="(.+?)"/', $getContents, $matches)) {
                        for ($i = 0; $i < count($matches[0]); $i++) {
                            $value = $matches[1][$i];
                            if ( ! \Illuminate\Support\Str::startsWith($value, $ruler)) {
                                $relative = $this->replacer($value, $fileInfo);
                                $getContents = str_replace('href="' . $value . '"', 'href="' . $relative . '"', $getContents);
                            }
                        }
                    }

                    if (preg_match_all('/src="(.+?)"/', $getContents, $matches)) {
                        for ($i = 0; $i < count($matches[0]); $i++) {
                            $value = $matches[1][$i];
                            if ( ! \Illuminate\Support\Str::startsWith($value, $ruler)) {
                                $relative = $this->replacer($value, $fileInfo);
                                $getContents = str_replace('src="' . $value . '"', 'src="' . $relative . '"', $getContents);
                            }
                        }
                    }

                    $this->filesystem->put($fileInfo->getRealPath(), $getContents);

                    break;
            }
        }

        $this->filesystem->copyDirectory(__DIR__ . '/vuejs.org/public', __DIR__ . '/app/src/main/assets');
    }

    /**
     * @return $this
     */
    protected function git()
    {
        if ( ! $this->filesystem->isDirectory(__DIR__ . '/vuejs.org')) {
            exec('git clone https://github.com/vuejs/vuejs.org.git ' . __DIR__ . '/vuejs.org', $output, $returnCode);
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function pkg()
    {
        exec('cd ' . __DIR__ . '/vuejs.org && yarn install 2>&1', $output, $returnCode);

        return $this;
    }

    /**
     * @return $this
     */
    protected function generate()
    {
        exec('cd ' . __DIR__ . '/vuejs.org && hexo generate', $output, $returnCode);

        return $this;
    }

    /**
     * @return array
     */
    protected function ruler()
    {
        return [
            'file:///android_asset/',
            '//',
            'http://',
            'https://',
            '_',
            '#',
        ];
    }

    /**
     * @param string $value
     * @param SplFileInfo $fileInfo
     * @return mixed
     */
    protected function replacer($value, SplFileInfo $fileInfo)
    {
        $android_asset = $value;
        if (preg_match('/(.*)#/', $value, $matches)) {
            $android_asset = $matches[1];
        }

        if (\Illuminate\Support\Str::startsWith($android_asset, "/")) {
            $specified = $this->directory . $android_asset;
        } else {
            $specified = realpath($fileInfo->getPath() . '/' . $android_asset);
        }

        if ( ! pathinfo($specified, PATHINFO_EXTENSION)) {
            if ( ! \Illuminate\Support\Str::endsWith($specified, ['\\', '/'])) {
                $specified .= DIRECTORY_SEPARATOR;
            }

            $specified .= 'index.html';
        }

        $absolute = \Illuminate\Support\Str::replaceFirst($this->directory, 'file:///android_asset', $specified);
        $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

        return $relative;
    }
}

(new Handler())->handle();
